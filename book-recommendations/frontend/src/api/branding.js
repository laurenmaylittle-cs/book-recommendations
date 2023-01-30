export async function getServiceName() {
  return await fetch('/api/branding/service-name').then(
    (response) => response.text())
}
