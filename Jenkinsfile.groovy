node{
  properties([parameters([string(defaultValue: 'dev', description: 'IP address', name: 'my_env', trim: true)]), pipelineTriggers([pollSCM('* * * * *')])])
    stage("Pull Repo"){
      git 'git@github.com:Nurjan87/cool_website.git'
    }
    stage("Install Web_Server"){
      sh "ssh ec2-user@${my_env} sudo yum install httpd -y"
    }

    stage("Copy Index File"){
      sh "scp index.html ec2-user@${my_env}/tmp/"
    }

    stage("Move Index File"){
      sh "ssh ec2-user@${my_env} sudo mv /tmp/index.html /var/www/html/"
    }

    stage("Restart Apache"){
      sh "ssh ec2-user@${my_env} sudo systemctl restart httpd && systemctl enable httpd"
    }
}