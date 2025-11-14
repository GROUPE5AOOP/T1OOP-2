pipeline {
    agent any

    // Parameter to choose the provisioning tool
    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select the provisioning tool')
    }

    environment {
        // Replace these with your AWS Access Key and Secret Key
        AWS_ACCESS_KEY_ID = "YOUR_AWS_ACCESS_KEY_ID"
        AWS_SECRET_ACCESS_KEY = "YOUR_AWS_SECRET_ACCESS_KEY"
        // Optional AWS region
        AWS_DEFAULT_REGION = "us-east-1"
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
                        bat 'terraform --version'
                    } else if (params.TOOL == 'Ansible') {
                        bat 'ansible --version'
                    } else if (params.TOOL == 'Helm') {
                        bat 'helm version'
                    }
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        bat '''
                        cd terraform
                        terraform init
                        terraform plan -out=tfplan
                        terraform apply -auto-approve tfplan
                        '''
                    } else if (params.TOOL == 'Ansible') {
                        bat '''
                        cd ansible
                        ansible-playbook -i hosts setup.yml
                        '''
                    } else if (params.TOOL == 'Helm') {
                        bat '''
                        set KUBECONFIG=%KUBE_CONFIG%
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
            echo "✅ Infrastructure deployed successfully using ${params.TOOL}"
        }
        failure {
            echo "❌ Deployment failed for ${params.TOOL}"
        }
    }
}
