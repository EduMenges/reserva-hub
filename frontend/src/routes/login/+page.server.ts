import { redirect, type Actions } from "@sveltejs/kit";

export const actions: Actions = {
  login: () => {},
  as_admin: () => {
    redirect(303, "/admin");
  },
};
