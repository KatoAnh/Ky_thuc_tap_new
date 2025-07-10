<template>
  <v-row dense>
    <v-col
      v-for="n in notes"
      :key="n.id"
      cols="12"
      sm="6"
      md="4"
      class="d-flex"
    >
      <NoteItem
        :note="n"
        class="flex-grow-1"
        @delete="$emit('delete', $event)"
        @edit="$emit('edit', $event)"
      />
    </v-col>
  </v-row>
</template>

<script setup>
import NoteItem from './NoteItem.vue';
defineProps(['notes']);
defineEmits(['delete', 'edit']);
</script>
