import type { EntryStatus, HistoryEntry } from "$lib/ApiTypes";

export function isEditable({ status, startTime }: { status: EntryStatus; startTime: Date }): boolean {
    return (status === "ACTIVE" || status === "AWAITING_APPROVAL") && startTime > new Date();
}
