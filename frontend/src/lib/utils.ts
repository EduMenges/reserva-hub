import * as Api from "$lib/ApiTypes.d";

export function currentDay() {
    return new Date(Date.now()).toISOString().split('T')[0]
}

export function hours(date: Date) {
    return date.toLocaleTimeString([], { timeStyle: "short" });
}

export function readableEntityType(entityType: Api.Entity) {
    switch (entityType) {
        case "EDITION_REQUEST":
            return "Edição";
        case "RESERVATION": return "Reserva";
        case "RESERVATION_REQUEST": return "Pedido de reserva";
    }
}