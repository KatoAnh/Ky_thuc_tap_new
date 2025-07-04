<template>
  <form
    @submit.prevent="handleSubmit"
    class="flex gap-2 mb-6 animate__animated animate__bounceIn"
  >
    <input
      v-model="title"
      type="text"
      placeholder="Thêm một công việc mới..."
      class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
    />
    <button
      type="submit"
      :disabled="!isFormValid"
      class="text-white p-3 rounded-lg transition-colors"
      :class="{
        'bg-blue-500 hover:bg-blue-600': isFormValid,
        'bg-gray-400 cursor-not-allowed': !isFormValid,
      }"
    >
      Thêm
    </button>
  </form>
</template>

<script>
import { ref, computed } from 'vue';

export default {
  name: 'TaskForm',
  emits: ['submit'],
  setup(props, { emit }) {
    const title = ref('');
    const isFormValid = computed(() => title.value.trim() !== '');
    const handleSubmit = () => {
      if (isFormValid.value) {
        const newTask = {
          id: Date.now(),
          title: title.value.trim(),
          completed: false,
        };
        emit('submit', newTask);
        title.value = '';
      }
    };
    return {
      title,
      isFormValid,
      handleSubmit,
    };
  },
};
</script>

<style scoped>
</style>