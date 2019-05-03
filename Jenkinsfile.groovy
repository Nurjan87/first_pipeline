node{
    stage("Pull Repo"){
      git 'https://github.com/Nurjan87/first_pipeline.git'
    }
    stage("Install Web_Server"){
      sh "ssh ec2-user@3.19.27.36 yum install httpd -y"
    }

    stage("Copy Index File"){
      sh "scp index.html ec2-user@3.19.27.36:/tmp/"
    }

    stage("Move Index File"){
      sh "ssh ec2-user@3.19.27.36 mv /tmp/index.html /var/www/html/"
    }

    stage("Restart Apache"){
      sh "systemctl restart httpd && systemctl enable httpd"
    }
}