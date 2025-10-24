import {render, screen} from "@testing-library/react";
import {describe, expect, it} from "vitest";
import {BlogCard} from "../components/BlogCard.tsx";
import type {BlogpostProps} from "../types/BlogpostProps.ts";
import {MemoryRouter} from "react-router-dom";

describe('Blog Card', () => {

    const args: BlogpostProps = {
        id: 1,
        timestamp: "timestamp",
        author: "author",
        subject: "subject",
        content: "content",
        likes: 5
    }

    it('should show image', () => {
        render(
            <MemoryRouter>
                <BlogCard {...args} />
            </MemoryRouter>);
        expect(screen.getByRole('img')).toBeInTheDocument();
    });

    it('should show subject', () => {
        render(
            <MemoryRouter>
                <BlogCard {...args} />
            </MemoryRouter>);
        expect(screen.getByText('subject')).toBeInTheDocument();
    });

    it('should show content', () => {
        render(
            <MemoryRouter>
                <BlogCard {...args} />
            </MemoryRouter>);
        expect(screen.getByText('content')).toBeInTheDocument();
    });

    it('should show timestamp', () => {
        render(
            <MemoryRouter>
                <BlogCard {...args} />
            </MemoryRouter>);
        expect(screen.getByText('timestamp')).toBeInTheDocument();
    });

    /*
    it('should show likes', () => {
        render(
            <MemoryRouter>
                <BlogCard {...args} />
            </MemoryRouter>);
        expect(screen.getByText(/likes/)).toBeInTheDocument();
    });

     */
});