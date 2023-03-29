package utils

import constants.JobStatus

class Common implements Serializable {

    private ctx

    Common(ctx) {
        this.ctx = ctx
    }

    def echo(str) {
        ctx.echo "${str}"
    }

    def sh(cmd) {
        this.shell(cmd, false)
    }

    def shell(cmd, stdoutRtn = true) {
        cmd = stdoutRtn ? "#!/bin/sh -e\n $cmd" : cmd
        def output = ctx.sh(script: cmd, returnStdout: stdoutRtn)
        if(stdoutRtn) {
            return output.trim()
        }
    }

    def getCurrBuildResult() {
        return ctx.currentBuild.result
    }

    def getPrevBuildResult() {
        if (ctx.currentBuild.previousBuild){
            return ctx.currentBuild.previousBuild.result
        }
        return JobStatus.SUCCESS
    }

    def getExecTime(){
        return ctx.currentBuild.durationString.minus(' and counting')
    }

    def getBuildUser() {
        return ctx.BUILD_USER ?: "GA-Trigger"
    }
}
