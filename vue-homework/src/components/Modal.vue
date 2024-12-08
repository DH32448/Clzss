<template>
  <div v-if="visible" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <slot></slot>
    </div>
  </div>
</template>
<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  visible: Boolean,
});

const emit = defineEmits(['update:visible']);

const closeModal = () => {
  console.log('模态框已关闭');
  emit('update:visible', false);
};
</script>


<style scoped>

</style>
