import { error } from "@sveltejs/kit";
import type { ErrorCodes, OkCodes } from "./ApiTypes";
import { errorCodes } from "./schemas";

const base = new URL("http://localhost:8080");

export enum RestMethods {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
}

type JSONData = string;
type AuthToken = string;

export const call = async <S, E>(
    method: RestMethods,
    path: string,
    body?: JSONData,
    token?: AuthToken
): Promise<{ status: OkCodes, data: S } | { status: ErrorCodes, data: E }> => {
    const headers = new Headers([["Content-Type", "application/json"]]);

    if (token) {
        headers.set("Authorization", `Bearer ${token}`);
    }

    const reqData: RequestInit = {
        body,
        method,
        headers,
    };

    const fullPath = new URL(path, base);

    return await fetch(fullPath, reqData)
        .then(async (response) => {
            const text = await response.text();
            const data = text ? JSON.parse(text) : {};

            if (response.status in errorCodes) {
                return { status: response.status as ErrorCodes, data: data as E };
            }

            return { status: response.status as OkCodes, data: data as S };
        })
        .catch((err) => {
            throw error(err);
        });
};

export const get = async<S, E>(path: string, args?: string, token?: string) => {
    return call<S, E>(RestMethods.GET, path + args, undefined, token);
}