<template>
  <li
    class="flex justify-between items-center p-3 border-b transition-colors duration-300 hover:bg-gray-50"
    :class="{ 'animate__animated animate__fadeIn': true }"
    :style="{ backgroundColor: task.completed ? '#f0f4f8' : 'white' }"
  >
    <div class="flex items-center">
      <input
        type="checkbox"
        :checked="task.completed"
        @change="$emit('toggle', task.id)"
        class="mr-3 h-5 w-5 rounded border-gray-300 text-blue-600 focus:ring-blue-500 cursor-pointer"
      />
      <span
        :class="{ 'line-through text-gray-500': task.completed }"
        class="transition-all"
      >
        {{ task.title }}
      </span>
    </div>

    <button
      @click="$emit('delete', task.id)"
      class="text-red-500 hover:text-red-700 transition-colors"
    >
      <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
      </svg>
    </button>
  </li>
</template>

<script>
export default {
  name: 'TaskItem',
  props: {
    task: {
      type: Object,
      required: true,
    },
  },
  emits: ['toggle', 'delete'],
};
</script>

<style scoped>
</style>