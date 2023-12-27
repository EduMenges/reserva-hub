export function currentDay() {
    return new Date(Date.now()).toISOString().split('T')[0]
}