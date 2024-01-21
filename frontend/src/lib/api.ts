import { error, type NumericRange } from "@sveltejs/kit";

const base = "http://localhost:8080";

export enum RestMethods {
  GET = "GET",
  POST = "POST",
  PUT = "PUT",
  DELETE = "DELETE",
}

async function send({ method, path, data, token }: { method: RestMethods; path: string; data?: any; token?: string }) {
  const headers = new Headers([["Content-Type", "application/json"]]);

  if (token) {
    headers.set("Authorization", `Token ${token}`);
  }

  const reqData: RequestInit = {
    ...(!(method === RestMethods.GET) ? { body: JSON.stringify(data) } : {}),
    method,
    headers,
  };

  const res = await fetch(`${base}/${path}`, reqData);
  if (res.ok || res.status === 422) {
    const text = await res.text();
    return text ? JSON.parse(text) : {};
  }

  throw error(res.status as NumericRange<400, 599>);
}

export function get(path: string, token: string) {
  return send({ method: RestMethods.GET, path, token });
}

export function del(path: string, token: string) {
  return send({ method: RestMethods.DELETE, path, token });
}

export function post(path: string, data: any, token?: string) {
  return send({ method: RestMethods.POST, path, data, token });
}

export function put(path: string, data: any, token: string) {
  return send({ method: RestMethods.PUT, path, data, token });
}
