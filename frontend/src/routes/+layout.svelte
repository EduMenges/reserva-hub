<script lang="ts">
    import { onMount } from "svelte";
    import { browser } from "$app/environment";

    import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler } from "@sveltestrap/sveltestrap";

    import "../app.scss";

    import type { PageData } from "./$types";
    import { autoMode, type ThemeOption } from "$lib/components/ThemeButton";
    import ThemeButton from "$lib/components/ThemeButton.svelte";

    onMount(async () => {
        if (!browser) return;

        await import("bootstrap");
    });

    let isOpen = false;

    let appTheme: ThemeOption = autoMode;

    export let data: PageData;
</script>

<Navbar expand="sm" sticky="top" container="sm" color="primary" class="mb-3">
    <NavbarBrand href="/">Início</NavbarBrand>
    <NavbarToggler on:click={() => (isOpen = !isOpen)} />
    <Collapse navbar expand="sm" {isOpen} class="justify-content-between">
        <Nav navbar>
            <ThemeButton bind:selected={appTheme} />
        </Nav>
    </Collapse>
</Navbar>

<main class="container m-auto pb-5">
    <slot />
</main>
