<template>
  <div class="mt-4 bg-white rounded-lg shadow-md">
    <transition-group name="task-list" tag="ul">
      <TaskItem
        v-for="task in tasks"
        :key="task.id"
        :task="task"
        @toggle="$emit('toggle', $event)"
        @delete="$emit('delete', $event)"
      />
    </transition-group>
  </div>
</template>

<script>
import TaskItem from './TaskItem.vue';

export default {
  name: 'TaskList',
  components: {
    TaskItem,
  },
  props: {
    tasks: {
      type: Array,
      required: true,
    },
  },
  emits: ['toggle', 'delete'],
};
</script>

<style scoped>
.task-list-enter-active,
.task-list-leave-active {
  transition: all 0.5s ease;
}
.task-list-enter-from,
.task-list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
.task-list-leave-active {
  position: absolute;
}
.task-list-move {
  transition: transform 0.4s ease;
}
</style>