import { error } from "@sveltejs/kit";
import { RestMethods, call } from "./ApiHelpers";
import { errorCodesSchema, schema } from "./schemas";

export async function GetHistory(token: string) {
    const historyReply = await call<History, Error>(RestMethods.GET, "history/get", undefined, token);

    if ("error" in historyReply) {
        throw error(historyReply.status, historyReply.error);
    }

    return schema.userHistoryEntry.parse(historyReply.data);
}