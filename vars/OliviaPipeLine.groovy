import test.utils.WebhookUtil


void call(Map params = [:], Closure buildScript){
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
