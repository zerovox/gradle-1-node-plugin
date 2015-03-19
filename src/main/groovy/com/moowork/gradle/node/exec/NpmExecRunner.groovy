package com.moowork.gradle.node.exec

import org.gradle.api.Project
import org.gradle.process.ExecResult

class NpmExecRunner
    extends ExecRunner
{
    public NpmExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    protected ExecResult doExecute()
    {
        if ( !this.ext.download && !this.ext.downloaded )
        {
            return run( 'npm', this.arguments )
        }

        def runner = new NodeExecRunner( this.project )
        runner.arguments = [this.variant.npmScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        return runner.execute()
    }
}
