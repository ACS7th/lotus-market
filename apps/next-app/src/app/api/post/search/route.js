import axios from "axios";

export async function GET(req) {
    const serverUrl = `http://${process.env.API_SERVER_URL || "localhost:8765"}`;

    try {
        const response = await axios.get(`${serverUrl}/api/post/search?item=${req.nextUrl.searchParams.get("item")}`);
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
