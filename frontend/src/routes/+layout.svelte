<script lang="ts">
    import { onMount } from "svelte";
    import { browser } from "$app/environment";

    import {
        Badge,
        Button,
        Collapse,
        Nav,
        Navbar,
        NavbarBrand,
        NavbarToggler,
        NavItem,
    } from "@sveltestrap/sveltestrap";

    import "../app.scss";

    import { autoMode, type ThemeOption } from "$lib/components/ThemeButton";
    import ThemeButton from "$lib/components/ThemeButton.svelte";
    import type { LayoutData } from "./$types";
    import UserBadge from "$lib/components/UserBadge.svelte";

    onMount(async () => {
        if (!browser) return;

        await import("bootstrap");
    });

    let isOpen = false;

    let appTheme: ThemeOption = autoMode;

    export let data: LayoutData;
</script>

<Navbar expand="sm" sticky="top" container="sm" color="primary" class="mb-3">
    <NavbarBrand href="/">Início</NavbarBrand>
    <NavbarToggler on:click={() => (isOpen = !isOpen)} />
    <Collapse navbar expand="sm" {isOpen} class="justify-content-end">
        <Nav navbar class="align-items-center gap-2">
            <ThemeButton bind:selected={appTheme} />
            {#if data.user}
            <NavItem>
                <UserBadge user={data.user} />
            </NavItem>
                <NavItem>
                    <Button color="danger">Sair</Button>
                    </NavItem>
            {/if}
        </Nav>
    </Collapse>
</Navbar>

<main class="container m-auto pb-5">
    <slot />
</main>
