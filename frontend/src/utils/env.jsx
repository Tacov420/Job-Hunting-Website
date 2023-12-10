export const env = {
    //VITE_API_URL: import.meta.env.VITE_API_URL,
    VITE_API_URL: "http://localhost:8005/api"

  };
  
  if (env.VITE_API_URL === undefined) {
    throw new Error("VITE_API_URL is undefined");
  }
  