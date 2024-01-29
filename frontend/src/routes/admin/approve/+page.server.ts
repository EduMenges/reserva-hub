import { error, type Actions } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load = (async () => {
    return {};
}) satisfies PageServerLoad;

export const actions: Actions = {
    postApprove: async () => { throw error(501) },
    postDelete: async () => { throw error(501) },
};