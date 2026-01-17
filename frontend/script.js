const BASE_URL = "https://speak-translate-backend-production.up.railway.app/";

async function translateText() {
    alert("Button clicked");

    try {
        const response = await fetch(BASE_URL + "/translate", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                text: "hello",
                targetLanguage: "hi"
            })
        });

        alert("Response status: " + response.status);

        const data = await response.json();
        alert(JSON.stringify(data));

        document.getElementById("outputText").value = data.translatedText || "";

    } catch (error) {
        alert("ERROR: " + error);
        console.error(error);
    }
}
