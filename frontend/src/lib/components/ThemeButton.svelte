<script lang="ts">
  import { onMount } from "svelte";

  interface ThemeOption {
    name: string;
    set(): void;
  }

  const lightMode: ThemeOption = {
    name: "☀️ Claro",
    set() {
      const doc = document.querySelector("html");
      doc?.setAttribute("data-bs-theme", "light");
    },
  };

  const darkMode: ThemeOption = {
    name: "🌒 Escuro",
    set() {
      const doc = document.querySelector("html");
      doc?.setAttribute("data-bs-theme", "dark");
    },
  };

  const autoMode: ThemeOption = {
    name: "🌗 Automático",
    set() {
      if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
        darkMode.set();
      } else {
        lightMode.set();
      }
    },
  };

  let selected: ThemeOption = autoMode;

  onMount(() => {
    selected.set();
  });
</script>

<div class="nav-item dropdown">
  <button class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown">
    {selected.name}
  </button>
  <ul class="dropdown-menu">
    {#each [lightMode, darkMode, autoMode] as opt}
      <li>
        <button
          class="dropdown-item"
          type="button"
          on:click={() => {
            selected = opt;
            selected.set();
          }}>{opt.name}</button
        >
      </li>
    {/each}
  </ul>
</div>
