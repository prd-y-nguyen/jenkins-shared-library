package utils

import groovy.json.JsonBuilder


class WebhookUtil implements Serializable {
    private ctx

    Common cm
    
    final String WEBHOOK_URL = "https://ynguyen.ap.ngrok.io/webhook/jenkins"
    final String WEBHOOK_DEV_URL = "https://ynguyen.dev.ap.ngrok.io/webhook/jenkins"

    WebhookUtil(ctx) {
        cm = new Common(ctx)
        this.ctx = ctx
    }
    
    private buildJSONFromMap(Map map) {
      def builder = new JsonBuilder()

      builder {
          map.each { key, value ->
            "$key" "$value"
          }
      }

      builder.toString()
    }

    private post(url, Map body) {
        def jsonBody = this.buildJSONFromMap(body)
      
        def connection = new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonBody);

        def responseCode = connection.getResponseCode();

        if (responseCode.equals(200)) {
            return connection.getInputStream().getText();
        }

        return null;
    }

    def sendToWebhook(Map payload = [:]) {
        String status = cm.getCurrBuildResult()

        payload.status = status

        this.post(WEBHOOK_URL, payload)
        this.post(WEBHOOK_DEV_URL, payload)
    }
}
