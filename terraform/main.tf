terraform {
  required_providers {
    local = {
      source  = "hashicorp/local"
      version = "2.4.0"
    }
  }
}

provider "local" {}

# Create multiple local files
resource "local_file" "file1" {
  filename = "file1.txt"
  content  = "Hello from Terraform running in Jenkins!"
}

resource "local_file" "file2" {
  filename = "file2.txt"
  content  = "Another file managed by Terraform"
}

resource "local_file" "file3" {
  filename = "file3.txt"
  content  = "Terraform can create many files locally"
}

# Output the list of files created
output "file_list" {
  value = [local_file.file1.filename, local_file.file2.filename, local_file.file3.filename]
}
