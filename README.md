# Virtual machines / Container used in CS 8803-O08

The class CS 8803-O08 provides the following scripts to help configure a local
test environment using either Vagrant or Docker.

The test environment is based on Ubuntu 18.04 with ANTLR 4.9.3 and SPIM with keepstats.
The runtime is compiled and installed in `/usr/local/lib`.

## Vagrant virtual machine

#### Usage

 1. Install [VirtualBox](https://www.virtualbox.org), and optionally its Extension Pack if you want to share the `data` directory between the host machine and the VM.
 1. Install [Vagrant](https://www.vagrantup.com).
 1. Clone this repository to your local machine.
 1. Optionally modify the `Vagrantfile` and install Vagrant plugin `vagrant-vbguest`.
 1. Run command `vagrant up` in the same directory where this readme is located.
 1. Wait for ~30 minutes while Vagrant downloads additional files and sets up the VM.

When done, you can issue the command `vagrant ssh` to login to the VM.

For detailed setup procedures, read the `Vagrantfile`.

## Docker image

#### Usage
Some of the following commands may require admin privileges or use of sudo.
1. Install [Docker](https://www.docker.com/)
2. Create directory: `mkdir cs8803_docker`
3. Download `Dockerfile`, `Docker_install.sh`, and `spimfixes.patch` into the `cs8803_docker` directory. 
4. Run command `docker build -t cs8803 ./cs8803_docker`
5. Run command `docker run -dp 2222:22 cs8803`

Use the command `ssh user@localhost -p 2222` to login to the docker container. username='user' password='password'

On a Mac, the following alternate run command may be helpful. It creates a container named cs8803 and adds a volume mapped folder. Replace HostPath/ContainerPath accordingly.

```text
docker run -it --name cs8803 -dp 2222:22 \
    --cap-add=SYS_PTRACE --security-opt seccomp=unconfined \
    -v [HostPath]:[ContainerPath] cs8803
```
