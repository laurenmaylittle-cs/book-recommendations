export async function getTestData () {
  return await fetch('http://localhost:8080/api/test').then((response) => response.text())
}

