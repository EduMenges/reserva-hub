import { error } from "@sveltejs/kit";
import type { OkStatus, ErrorStatus } from "./ApiTypes";

const base = new URL("http://localhost:8080");

export enum RestMethods {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
}

type JSONData = string;
type AuthToken = string;

export const call = async <T>(
    method: RestMethods,
    path: string,
    body?: JSONData,
    token?: AuthToken
): Promise<{ status: ErrorStatus | OkStatus; data: T }> => {
    const headers = new Headers([["Content-Type", "application/json"]]);

    if (token) {
        headers.set("Authorization", `Token ${token}`);
    }

    const reqData: RequestInit = {
        ...(!(method === RestMethods.GET) ? { body } : {}),
        method,
        headers,
    };

    const fullPath = new URL(path, base);

    return await fetch(fullPath, reqData)
        .then(async (response) => {
            const text = await response.text();
            const data = text ? JSON.parse(text) : {};

            if (response.status >= 400 && response.status <= 599) {
                return { status: response.status as ErrorStatus, data };
            }
            return { status: response.status as OkStatus, data };
        })
        .catch((err) => {
            throw error(err);
        });
};
