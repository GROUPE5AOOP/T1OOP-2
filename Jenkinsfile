pipeline {
    agent any

    // Parameter to choose the provisioning tool
    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select the provisioning tool')
    }

    environment {
        AWS_CREDENTIALS = credentials('aws-jenkins-creds') // AWS credentials in Jenkins
        KUBE_CONFIG = credentials('kubeconfig-jenkins')    // K8s kubeconfig if using Helm
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/GROUPE5AOOP/T1OOP-2.git'
            }
        }

        stage('Setup') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        sh 'terraform --version'
                    } else if (params.TOOL == 'Ansible') {
                        sh 'ansible --version'
                    } else if (params.TOOL == 'Helm') {
                        sh 'helm version'
                    }
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        sh '''
                        cd terraform
                        terraform init
                        terraform plan -out=tfplan
                        terraform apply -auto-approve tfplan
                        '''
                    } else if (params.TOOL == 'Ansible') {
                        sh '''
                        cd ansible
                        ansible-playbook -i hosts setup.yml
                        '''
                    } else if (params.TOOL == 'Helm') {
                        sh '''
                        export KUBECONFIG=$KUBE_CONFIG
                        cd helm
                        helm upgrade --install myapp ./myapp
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Infrastructure deployed successfully using ${params.TOOL}"
        }
        failure {
            echo "Deployment failed for ${params.TOOL}"
        }
    }
}
