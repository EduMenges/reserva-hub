<script lang="ts">
  import { Dropdown, DropdownItem, DropdownMenu, DropdownToggle, NavItem } from "@sveltestrap/sveltestrap";
  import { onMount } from "svelte";
    import { lightMode, darkMode, autoMode, type ThemeOption } from "./ThemeButton";

  export let selected: ThemeOption;
</script>

<Dropdown nav>
  <DropdownToggle caret>
    <i class={`me-2 bi-${selected.icon}`}></i>
    Tema
  </DropdownToggle>
  <DropdownMenu>
    {#each [lightMode, darkMode, autoMode] as opt}
      <DropdownItem
        on:click={() => {
          selected = opt;
          selected.set();
        }}
      >
        {opt.description}
      </DropdownItem>
    {/each}
  </DropdownMenu>
</Dropdown>
