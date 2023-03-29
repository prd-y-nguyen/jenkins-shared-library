import groovy.json.JsonOutput

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection

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

final String WEBHOOK_URL = "https://ynguyen.ap.ngrok.io/webhook/jenkins"
final String WEBHOOK_DEV_URL = "https://ynguyen.dev.ap.ngrok.io/webhook/jenkins"

def url = WEBHOOK_DEV_URL

def body = [
    "payload": [
        "status": "SUCCESS"
    ]
]

def payload = JsonOutput.toJson(body)

println "Sending payload to $url"

sendPostRequest(url, payload)
