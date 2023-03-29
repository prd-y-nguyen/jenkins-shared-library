package utils

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

    private post(url, Map body) {      
        def connection = new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(JsonOutput.toJson(body));

        def responseCode = connection.getResponseCode();

        if (responseCode.equals(200)) {
            return connection.getInputStream().getText();
        }

        return null;
    }

    def sendToWebhook(List payload) {
        String status = cm.getCurrBuildResult()

        payload.push(status)

        this.post(WEBHOOK_URL, [
          "payload": payload
        ])
        this.post(WEBHOOK_DEV_URL, [
          "payload": payload
        ])
    }
}
