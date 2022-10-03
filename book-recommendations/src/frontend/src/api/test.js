export async function getTestData() {
    return await fetch("http://localhost:3000/api/test").then((response) => response.text());
}
