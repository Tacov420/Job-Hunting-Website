# job hunting



## How to run

### Frontend

```bash
# install npm if you don't have it
sudo apt install npm

# install Yarn with npm
sudo npm install -g yarn

# currently you should be at the project root
cd frontend
yarn
yarn dev --host
```



### Backend

```bash
# install maven if you don't have it
sudo apt-get install maven

# currently you should be at the project root
cd backend
mvn spring-boot:run
```



After executing the above steps, you should see the page at `localhost:5173` or `localhost:5174`





## DevOps

`.gitlab-ci.yml`

```yaml
stages:          # List of stages for jobs and their order of execution
  - build-images
  - deploy


build-images:
  stage: build-images
  image: docker:latest
  services:
    - docker:dind
  script:
    - docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY
    - docker build --file frontend/Dockerfile -t registry.gitlab.com/software-engineering8932241/job-hunting:frontend_image_$CI_COMMIT_SHORT_SHA .
    - docker build --file backend/Dockerfile -t registry.gitlab.com/software-engineering8932241/job-hunting:backend_image_$CI_COMMIT_SHORT_SHA .
    - docker push registry.gitlab.com/software-engineering8932241/job-hunting:frontend_image_$CI_COMMIT_SHORT_SHA
    - docker push registry.gitlab.com/software-engineering8932241/job-hunting:backend_image_$CI_COMMIT_SHORT_SHA

deploy:
  variables:
    CONTAINER_REGISTRY: registry.gitlab.com/software-engineering8932241/job-hunting
  image: ubuntu:latest
  stage: deploy
  before_script:
    - apt-get -yq update
    - apt-get -yqq install ssh
    - install -m 600 -D /dev/null ~/.ssh/id_rsa
    - echo "$SSH_PRIVATE_KEY"
    - echo "$SSH_PRIVATE_KEY" | base64 -d > ~/.ssh/id_rsa
    - ssh-keyscan -H $HOST_ADDR > ~/.ssh/known_hosts
  script:
    - ssh group4@$HOST_ADDR "./cleanup.sh && docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY && docker pull $CONTAINER_REGISTRY:frontend_image_$CI_COMMIT_SHORT_SHA && docker run -d -t --name frontend_$CI_COMMIT_SHORT_SHA -p 5173:5173 $CONTAINER_REGISTRY:frontend_image_$CI_COMMIT_SHORT_SHA && docker pull $CONTAINER_REGISTRY:backend_image_$CI_COMMIT_SHORT_SHA && docker run -d -t --name backend_$CI_COMMIT_SHORT_SHA -p 8005:8005 $CONTAINER_REGISTRY:backend_image_$CI_COMMIT_SHORT_SHA && docker exec -d backend_$CI_COMMIT_SHORT_SHA mvn spring-boot:run && docker exec -d frontend_$CI_COMMIT_SHORT_SHA yarn dev --host && exit"
  after_script:
    - rm -rf ~/.ssh

```

- Set CI/CD variables `SSH_PRIVATE_KEY`, `HOST_ADDR` on your Gitlab repo. `SSH_PRIVATE_KEY` = SSH key generated on your host, `HOST_ADDR` = external IP of your host. 
- Make your own `cleanup.sh` (stop and remove all docker containers and images) on your host to avoid port confliction.

- Push source codes to Gitlab will automatically deploy to `HOST_ADDR`.
