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

    def sendPostRequest(urlString, paramString) {
        def url = new URL(urlString)
        def connection = url.openConnection()
        connection.setDoOutput(true)

        def writer = new OutputStreamWriter(connection.getOutputStream())

        writer.write(paramString)
        writer.flush()

        String line

        def reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))

        while ((line = reader.readLine()) != null) {
          println line
        }

        writer.close()
        reader.close()
    }

    def sendToWebhook(Map payload) {
        String status = cm.getCurrBuildResult()
        
        payload.status = status

        this.sendPostRequest(WEBHOOK_URL, [ "payload": JsonOutput.toJson(payload) ])
        this.sendPostRequest(WEBHOOK_DEV_URL, [ "payload": JsonOutput.toJson(payload) ])
    }
}
