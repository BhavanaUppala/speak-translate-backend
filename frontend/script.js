//const BASE_URL = "http://localhost:8080/api";
//const BASE_URL ="https://speak-translate-backend-production.up.railway.app/api"
const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

/* =========================
   SPEECH → TEXT
   ========================= */
async function recordSpeech() {
    try {
        const sourceLang = document.getElementById("sourceLang").value;

        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        const mediaRecorder = new MediaRecorder(stream);
        let audioChunks = [];

        mediaRecorder.ondataavailable = e => audioChunks.push(e.data);

        mediaRecorder.onstop = async () => {
            const audioBlob = new Blob(audioChunks, { type: "audio/webm" });

            const formData = new FormData();
            formData.append("audio", audioBlob);
            formData.append("sourceLang", sourceLang);

            const response = await fetch(BASE_URL + "/speech-to-text", {
                method: "POST",
                body: formData
            });

            const data = await response.json();
            document.getElementById("inputText").value = data.text;
        };

        mediaRecorder.start();
        setTimeout(() => mediaRecorder.stop(), 3000);

    } catch (err) {
        alert("Mic error: " + err.message);
    }
}

/* =========================
   TEXT → TRANSLATION
   ========================= */
async function translateText() {
    const inputEl = document.getElementById("inputText");
    const originalText = inputEl.value; // 🔒 preserve input

    if (!originalText.trim()) {
        alert("No text to translate");
        return;
    }

    const sourceLang = document.getElementById("sourceLang").value;
    const targetLang = document.getElementById("targetLang").value;

    const response = await fetch(BASE_URL + "/translate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            text: originalText,
            sourceLang: sourceLang,
            targetLang: targetLang
        })
    });

    const data = await response.json();

    // ✅ ONLY update output box
    document.getElementById("outputText").value = data.translatedText;

    // ✅ restore input text explicitly (safety)
    inputEl.value = originalText;
}

/* =========================
   TEXT → SPEECH
   ========================= */
async function speakText() {
    const text = document.getElementById("outputText").value;
    const targetLang = document.getElementById("targetLang").value;

    const response = await fetch(BASE_URL + "/text-to-speech", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            text: text,
            languageCode: targetLang
        })
    });

    const audioBlob = await response.blob();
    const audioUrl = URL.createObjectURL(audioBlob);
    new Audio(audioUrl).play();
}
