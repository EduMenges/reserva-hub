import { error } from "@sveltejs/kit";
import { RestMethods, call } from "./ApiHelpers";
import { errorCodesSchema, schema } from "./schemas";

export async function GetHistory(token: string) {
    const historyReply = await call<History, Error>(RestMethods.GET, "history/get", undefined, token);

    const errc = errorCodesSchema.safeParse(historyReply.status);
    if (errc.success) {
        throw error(errc.data);
    }

    return schema.userHistoryEntry.parse(historyReply.data);
}