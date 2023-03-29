import utils.WebhookUtil


void call(){
    WebhookUtil webhookUtil = new WebhookUtil(this)

    pipeline {
        post {
            always {
                script {
                    webhookUtil.sendToWebhook([
                        "Hello world",
                    ])
                }
            }
        }
    }
}
