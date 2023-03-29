package test.utils


class WebhookUtil implements Serializable {
    private ctx

    Common cm
    
    final String WEBHOOK_URL = "https://ynguyen.ap.ngrok.io/webhook/jenkins"
    final String WEBHOOK_DEV_URL = "https://ynguyen.dev.ap.ngrok.io/webhook/jenkins"

    WebhookUtil(ctx) {
        cm = new Common(ctx)
        this.ctx = ctx
    }


    def sendToWebhook(String[] payload) {
        String status = cm.getCurrBuildResult()
        
        payload.push("JOB_STATUS:${status}")
      
        this.post(WEBHOOK_URL, payload)
        this.post(WEBHOOK_DEV_URL, payload)
    }
    
    def post(url, body) {
        def connection = new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(body.getBytes());

        def responseCode = connection.getResponseCode();

        if (responseCode.equals(200)) {
            return connection.getInputStream().getText();
        }

        return null;
    }
}
