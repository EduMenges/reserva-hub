<script lang="ts">
  let className = "";
  export { className as class };

  export let style = "";
</script>

<section class={`m-auto ${className}`} {style}>
  <slot />
</section>

<style lang="scss">
  @import "../../variables.scss";

  section {
    max-width: map-get($grid-breakpoints, sm) * 0.65;
  }
</style>
