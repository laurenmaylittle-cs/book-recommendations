export async function getBranding() {
  return await fetch('http://localhost:8080/api/branding').then(
    (response) => response.text())
}
