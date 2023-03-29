import utils.WebhookUtil


void call(){
    WebhookUtil webhookUtil = new WebhookUtil(this)

    pipeline {
        webhookUtil.sendToWebhook([
            message: "Hello world"
        ])
    }
}
