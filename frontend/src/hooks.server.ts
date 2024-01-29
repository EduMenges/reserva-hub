import type { Handle } from "@sveltejs/kit";

export const handle: Handle = async ({ event, resolve }) => {
  const jwt = event.cookies.get("jwt");
  event.locals.user = jwt ? JSON.parse(atob(jwt)) : null;

  return await resolve(event);
};
