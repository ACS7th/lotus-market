import axios from "axios";

export async function GET(req) {
    const serverUrl = `http://${process.env.API_SERVER_URL || "localhost:8765"}`;

    try {
        const response = await axios.get(`${serverUrl}/api/post`);
        return new Response(JSON.stringify(response.data), {
            status: 200,
            headers: { "Content-Type": "application/json" },
        });
    } catch (error) {
        console.error("Error fetching posts:", error);
        return new Response(
            JSON.stringify({ message: "Error fetching posts" }),
            { status: 500, headers: { "Content-Type": "application/json" } }
        );
    }
}

export async function POST(req) {
    const serverUrl = process.env.API_SERVER_URL
        ? `http://${process.env.API_SERVER_URL}`
        : "http://localhost:8765";

    try {
        const formData = await req.formData(); 
        const response = await axios.post(`${serverUrl}/api/post`, formData);

        return new Response(JSON.stringify(response.data), {
            status: response.status,
            headers: { "Content-Type": "application/json" },
        });
    } catch (error) {
        console.error("Error submitting post:", error);
        return new Response(
            JSON.stringify({ message: "Error submitting post" }),
            { status: 500, headers: { "Content-Type": "application/json" } }
        );
    }
}

