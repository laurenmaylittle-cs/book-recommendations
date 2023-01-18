export async function getServiceName() {
  return await fetch('http://localhost:8080/api/branding/service-name').then(
    (response) => response.text())
}
