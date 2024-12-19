# Build
custom_build(
  ref = 'catalog-service',
  command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
  deps = ['build.gradle', 'src']
)

# Deployment
k8s_yaml(['k8s/deployment.yaml', 'k8s/service.yaml'])

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])