package utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection

import groovy.json.JsonOutput


class WebhookUtil implements Serializable {
    private ctx

    Common cm

    final String WEBHOOK_URL = "https://ynguyen.ap.ngrok.io/webhook/jenkins"
    final String WEBHOOK_DEV_URL = "https://ynguyen.dev.ap.ngrok.io/webhook/jenkins"

    WebhookUtil(ctx) {
        cm = new Common(ctx)
        this.ctx = ctx
    }

    def sendPostRequest(String urlString, String bodyString) {
        println "Sending POST request to ${urlString} with body ${bodyString}"

        def url = new URL(urlString)
        def connection = url.openConnection()
        // connection.setConnectTimeout(10000);
        // connection.setReadTimeout(10000);
        connection.setDoOutput(true)

        try {
            def writer = new OutputStreamWriter(connection.getOutputStream())

            writer.write(bodyString)
            writer.flush()

            def reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))

            writer.close()
            reader.close()
        } catch (Exception e) {
            println "Webhook is triggered, but the server failed to response:"
            println e
        } finally {
            connection.disconnect();
        }
    }

    def sendToWebhook(Map payload) {
        String status = cm.getCurrBuildResult()
        
        payload.status = status

        // this.sendPostRequest(WEBHOOK_URL, JsonOutput.toJson(payload))
        this.sendPostRequest(WEBHOOK_DEV_URL, JsonOutput.toJson(payload))
    }
}
