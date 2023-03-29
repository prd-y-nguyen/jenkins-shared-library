import groovy.json.JsonOutput

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection

def sendPostRequest(String urlString, String paramString) {
    println "Sending POST request to ${urlString} with params ${paramString}"
  
    def url = new URL(urlString)
    def connection = url.openConnection()
    connection.setDoOutput(true)

    def writer = new OutputStreamWriter(connection.getOutputStream())

    writer.write(paramString)
    writer.flush()

    def reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))

    writer.close()
    reader.close()
}

def sendToWebhook(Map payload) {
    final String WEBHOOK_URL = "https://ynguyen.ap.ngrok.io/webhook/jenkins"
    final String WEBHOOK_DEV_URL = "https://ynguyen.dev.ap.ngrok.io/webhook/jenkins"

    payload.status = "SUCCESS"

    // this.sendPostRequest(WEBHOOK_URL, JsonOutput.toJson(payload))
    this.sendPostRequest(WEBHOOK_DEV_URL, JsonOutput.toJson(payload))
}

sendToWebhook([
    "message" : "Hello world"
])
