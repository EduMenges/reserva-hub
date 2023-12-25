<script lang="ts">
  import { onMount } from "svelte";
  import "../app.scss";
  import { browser } from "$app/environment";

  onMount(async () => {
    if (!browser) return;

    await import("bootstrap");
  });

  import ThemeButton from "$lib/components/ThemeButton.svelte";
  import { page } from "$app/stores";

  type Route = {
    url: string;
    display: string;
  };

  const routes: Route[] = [{ url: "/login", display: "Login" }];
</script>

<nav class="navbar container navbar-expand-lg">
  <a href="/" class="navbar-brand">Início</a>
  <button
    class="navbar-toggler"
    type="button"
    data-bs-toggle="collapse"
    data-bs-target="#navbarSupportedContent"
    aria-controls="navbarSupportedContent"
    aria-expanded="false"
    aria-label="Toggle navigation"
  >
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse d-flex flex-row flex-wrap justify-content-between" id="navbarSupportedContent">
    <ul class="navbar-nav">
      {#each routes as route}
        <li class="nav-item">
          <a href={route.url} class="nav-link" class:active={$page.route.id == route.url}>{route.display}</a>
        </li>
      {/each}
    </ul>
    <ul class="navbar-nav">
      <ThemeButton />
    </ul>
  </div>
</nav>

<main class="container">
  <slot />
</main>
