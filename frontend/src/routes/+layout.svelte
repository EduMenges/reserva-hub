<script lang="ts">
  import ThemeButton from "$lib/components/ThemeButton.svelte";
  import "../app.scss";
</script>

<nav class="navbar container">
  <ul class="navbar-nav">
    <li class="nav-item">
      <a href="/" class="nav-link">Início</a>
    </li>
  </ul>
  <ThemeButton />
</nav>

<main class="container">
  <slot />
</main>
