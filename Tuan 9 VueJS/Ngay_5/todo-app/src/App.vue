<template>
  <div id="app">
    <div class="container">
      <header>
        <h1 class="title">📝 To-Do List</h1>
        <p class="subtitle">Quản lý công việc hằng ngày của bạn</p>
      </header>
      <main>
        <TaskForm @submit="handleAddTask" />
        <TaskList
          :tasks="tasks"
          @toggle="handleToggleTask"
          @delete="handleDeleteTask"
        />
        <p v-if="tasks.length === 0" class="empty-message">
          Tuyệt vời! Không có công việc nào.
        </p>
      </main>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import TaskForm from './components/TaskForm.vue';
import TaskList from './components/TaskList.vue';

export default {
  name: 'App',
  components: { TaskForm, TaskList },
  setup() {
    const tasks = ref([]);
    onMounted(() => {
      const savedTasks = localStorage.getItem('tasks');
      if (savedTasks) tasks.value = JSON.parse(savedTasks);
    });
    watch(tasks, (newTasks) => {
      localStorage.setItem('tasks', JSON.stringify(newTasks));
    }, { deep: true });
    const handleAddTask = (newTask) => tasks.value.unshift(newTask);
    const handleToggleTask = (taskId) => {
      const task = tasks.value.find((t) => t.id === taskId);
      if (task) task.completed = !task.completed;
    };
    const handleDeleteTask = (taskId) => {
      tasks.value = tasks.value.filter((t) => t.id !== taskId);
    };
    return { tasks, handleAddTask, handleToggleTask, handleDeleteTask };
  },
};
</script>

<style>
:root {
  --app-bg: #f0f4f8;
  --container-bg: #ffffff;
  --text-primary: #1f2937;
  --text-secondary: #6b7280;
  --primary-color: #3b82f6;
  --primary-color-hover: #2563eb;
  --border-color: #e5e7eb;
}
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  background-color: var(--app-bg);
}
#app {
  padding: 2rem 1rem;
}
.container {
  max-width: 500px;
  margin: 0 auto;
  background-color: var(--container-bg);
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
}
header {
  text-align: center;
  margin-bottom: 2rem;
}
.title {
  font-size: 2.5rem;
  color: var(--text-primary);
  margin: 0;
}
.subtitle {
  color: var(--text-secondary);
  margin-top: 0.5rem;
}
.empty-message {
  text-align: center;
  color: var(--text-secondary);
  margin-top: 2rem;
}
</style>