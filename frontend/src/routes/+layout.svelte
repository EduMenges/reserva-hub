<script lang="ts">
  import { onMount } from "svelte";
  import "../app.scss";
  import { browser } from "$app/environment";

  onMount(async () => {
    if (!browser) return;

    await import("bootstrap");
  });

  import { Collapse, NavItem, Nav, NavLink, Navbar, NavbarBrand, NavbarToggler } from "@sveltestrap/sveltestrap";
  import ThemeButton from "$lib/components/ThemeButton.svelte";
    import { autoMode, type ThemeOption } from "$lib/components/ThemeButton";

  type Route = {
    url: string;
    display: string;
  };

  const routes: Route[] = [{ url: "/login", display: "Login" }];

  let isOpen = false;

  let appTheme: ThemeOption = autoMode;
</script>

<Navbar expand="sm" sticky="top" container="sm" color="primary" class="mb-3" >
  <NavbarBrand href="/">Início</NavbarBrand>
  <NavbarToggler on:click={() => (isOpen = !isOpen)} />
  <Collapse navbar expand="sm" {isOpen} class="justify-content-between">
    <Nav navbar>
      {#each routes as route}
        <NavItem>
          <NavLink href={route.url}>{route.display}</NavLink>
        </NavItem>
      {/each}
    </Nav>
    <Nav navbar>
      <ThemeButton bind:selected={appTheme}/>
    </Nav>
  </Collapse>
</Navbar>

<main class="container m-auto pb-5">
  <slot />
</main>
